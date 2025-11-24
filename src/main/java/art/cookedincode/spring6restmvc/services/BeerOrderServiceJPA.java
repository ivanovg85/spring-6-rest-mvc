package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.controller.NotFoundException;
import art.cookedincode.spring6restmvc.entities.BeerOrder;
import art.cookedincode.spring6restmvc.entities.BeerOrderLine;
import art.cookedincode.spring6restmvc.entities.BeerOrderShipment;
import art.cookedincode.spring6restmvc.entities.Customer;
import art.cookedincode.spring6restmvc.mappers.BeerOrderMapper;
import art.cookedincode.spring6restmvc.model.BeerOrderCreateDTO;
import art.cookedincode.spring6restmvc.model.BeerOrderDTO;
import art.cookedincode.spring6restmvc.model.BeerOrderUpdateDTO;
import art.cookedincode.spring6restmvc.repositories.BeerOrderRepository;
import art.cookedincode.spring6restmvc.repositories.BeerRepository;
import art.cookedincode.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static art.cookedincode.spring6restmvc.services.BeerServiceJPA.buildPageRequest;

/**
 * Created by Georgi Ivanov.
 */
@Service
@RequiredArgsConstructor
public class BeerOrderServiceJPA implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;

    @Override
    public Optional<BeerOrderDTO> getById(UUID id) {
        return Optional.ofNullable(beerOrderMapper
                .beerOrderToBeerOrderDto(beerOrderRepository.findById(id)
                        .orElse(null)));
    }

    @Override
    public Page<BeerOrderDTO> listBeerOrders(Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        return beerOrderRepository.findAll(buildPageRequest(pageNumber, pageSize, sort))
                .map(beerOrderMapper::beerOrderToBeerOrderDto);
    }

    @Override
    public BeerOrderDTO createBeerOrder(BeerOrderCreateDTO beerOrderCreateDTO) {
        Customer customer = customerRepository.findById(beerOrderCreateDTO.getCustomerId())
                .orElseThrow(NotFoundException::new);
        Set<BeerOrderLine> beerOrderLines = new HashSet<>();
        beerOrderCreateDTO.getBeerOrderLines().forEach(beerOrderLine -> {
            beerOrderLines.add(BeerOrderLine.builder()
                    .beer(beerRepository.findById(beerOrderLine.getBeerId()).orElseThrow(NotFoundException::new))
                    .orderQuantity(beerOrderLine.getOrderQuantity())
                    .build());
        });

        return beerOrderMapper.beerOrderToBeerOrderDto(
                beerOrderRepository.save(BeerOrder.builder()
                        .customer(customer)
                        .beerOrderLines(beerOrderLines)
                        .customerRef(beerOrderCreateDTO.getCustomerRef())
                        .build())
        );
    }

    @Override
    public BeerOrderDTO updateBeerOrder(UUID beerOrderId, BeerOrderUpdateDTO beerOrderUpdateDTO) {
        val beerOrder = beerOrderRepository.findById(beerOrderId).orElseThrow(NotFoundException::new);
        beerOrder.setCustomer(customerRepository.findById(beerOrderUpdateDTO.getCustomerId()).orElseThrow(NotFoundException::new));
        beerOrder.setCustomerRef(beerOrderUpdateDTO.getCustomerRef());

        beerOrderUpdateDTO.getBeerOrderLines().forEach(beerOrderLine -> {
            if (beerOrderLine.getBeerId() != null) {
                val foundLine = beerOrder.getBeerOrderLines().stream()
                        .filter(beerOrderLine1 -> beerOrderLine1.getId().equals(beerOrderLine.getId()))
                        .findFirst().orElseThrow(NotFoundException::new);
                foundLine.setBeer(beerRepository.findById(beerOrderLine.getBeerId()).orElseThrow(NotFoundException::new));
                foundLine.setOrderQuantity(beerOrderLine.getOrderQuantity());
                foundLine.setQuantityAllocated(beerOrderLine.getQuantityAllocated());
            } else {
                beerOrder.getBeerOrderLines().add(BeerOrderLine.builder()
                        .beer(beerRepository.findById(beerOrderLine.getBeerId()).orElseThrow(NotFoundException::new))
                        .orderQuantity(beerOrderLine.getOrderQuantity())
                        .quantityAllocated(beerOrderLine.getQuantityAllocated())
                        .build());
            }
        });

        if (beerOrderUpdateDTO.getBeerOrderShipment() != null && beerOrderUpdateDTO.getBeerOrderShipment().getTrackingNumber() != null) {
            if (beerOrder.getBeerOrderShipment() == null) {
                beerOrder.setBeerOrderShipment(BeerOrderShipment.builder()
                        .trackingNumber(beerOrderUpdateDTO.getBeerOrderShipment().getTrackingNumber())
                        .build());
            } else {
                beerOrder.getBeerOrderShipment().setTrackingNumber(beerOrderUpdateDTO.getBeerOrderShipment().getTrackingNumber());
            }
        }

        return beerOrderMapper.beerOrderToBeerOrderDto(beerOrderRepository.save(beerOrder));
    }

    @Override
    public void deleteBeerOrder(UUID beerOrderId) {
        if (beerOrderRepository.existsById(beerOrderId)) {
            beerOrderRepository.deleteById(beerOrderId);
        } else  {
            throw new NotFoundException();
        }
    }
}
