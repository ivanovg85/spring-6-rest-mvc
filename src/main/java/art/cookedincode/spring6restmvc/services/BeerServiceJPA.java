package art.cookedincode.spring6restmvc.services;

import art.cookedincode.spring6restmvc.entities.Beer;
import art.cookedincode.spring6restmvc.events.BeerCreatedEvent;
import art.cookedincode.spring6restmvc.events.BeerDeletedEvent;
import art.cookedincode.spring6restmvc.events.BeerPatchedEvent;
import art.cookedincode.spring6restmvc.events.BeerUpdatedEvent;
import art.cookedincode.spring6restmvc.mappers.BeerMapper;
import art.cookedincode.spring6restmvc.model.BeerDTO;
import art.cookedincode.spring6restmvc.model.BeerStyle;
import art.cookedincode.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Georgi Ivanov
 */
@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    private final CacheManager cacheManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;
    private static final int MAX_PAGE_SIZE = 1000;

    @Cacheable(cacheNames = "beerListCache")
    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        log.info("List Beers - in service");

        Sort sort = Sort.by(Sort.Order.asc("beerName"));
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sort);

        Page<Beer> beerPage;

        if (StringUtils.hasText(beerName) && beerStyle == null) {
            beerPage = listBeersByName(beerName, pageRequest);
        } else if (!StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByStyle(beerStyle, pageRequest);
        } else if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByNameAndStyle(beerName, beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventory != null && !showInventory) {
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerPage.map(beerMapper::beerToBeerDto);
    }

    public static PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, Sort sort) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > MAX_PAGE_SIZE) {
                queryPageSize = MAX_PAGE_SIZE;
            } else {
                queryPageSize = pageSize;
            }
        }

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    public Page<Beer> listBeersByName(String beerName, PageRequest pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageRequest);
    }

    public Page<Beer> listBeersByStyle(BeerStyle beerStyle, PageRequest pageRequest) {
        return beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
    }

    private Page<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle, PageRequest pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%", beerStyle, pageRequest);
    }

    @Cacheable(cacheNames = "beerCache")
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.info("Get Beer by id - in service");

        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        if (cacheManager.getCache("beerListCache") != null) {
            cacheManager.getCache("beerListCache").clear();
        }

        val savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beer));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        applicationEventPublisher.publishEvent(new BeerCreatedEvent(savedBeer, authentication));

        return beerMapper.beerToBeerDto(savedBeer);
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        clearCache(beerId);

        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setQuantityOnHand(beer.getQuantityOnHand());

            val savedBeer = beerRepository.save(foundBeer);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            applicationEventPublisher.publishEvent(new BeerUpdatedEvent(savedBeer, authentication));

            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(savedBeer)));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteBeerById(UUID beerId) {

        clearCache(beerId);

        if (beerRepository.existsById(beerId)) {

            val auth = SecurityContextHolder.getContext().getAuthentication();

            applicationEventPublisher.publishEvent(new BeerDeletedEvent(Beer.builder().id(beerId).build(), auth));

            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {

        clearCache(beerId);
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            foundBeer.setBeerName(StringUtils.hasText(beer.getBeerName()) ? beer.getBeerName() : foundBeer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle() != null ? beer.getBeerStyle() : foundBeer.getBeerStyle());
            foundBeer.setUpc(StringUtils.hasText(beer.getUpc()) ? beer.getUpc() : foundBeer.getUpc());
            foundBeer.setPrice(beer.getPrice() != null ? beer.getPrice() : foundBeer.getPrice());
            foundBeer.setQuantityOnHand(beer.getQuantityOnHand() != null ? beer.getQuantityOnHand() : foundBeer.getQuantityOnHand());
            foundBeer.setUpdateDate(LocalDateTime.now());

            val savedBeer = beerRepository.save(foundBeer);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            applicationEventPublisher.publishEvent(new BeerPatchedEvent(savedBeer, authentication));

            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(savedBeer)));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    private void clearCache(UUID beerId) {
        if (cacheManager.getCache("beerCache") != null) {
            cacheManager.getCache("beerCache").evict(beerId);
        }
        if (cacheManager.getCache("beerListCache") != null) {
            cacheManager.getCache("beerListCache").clear();
        }
    }
}
