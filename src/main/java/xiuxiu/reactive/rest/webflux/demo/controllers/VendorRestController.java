package xiuxiu.reactive.rest.webflux.demo.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xiuxiu.reactive.rest.webflux.demo.domain.Vendor;
import xiuxiu.reactive.rest.webflux.demo.repository.VendorRepository;

import java.util.Objects;

@RestController
public class VendorRestController {

    private final VendorRepository vendorRepository;

    public VendorRestController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @ApiOperation(value="getVendorList", notes="You may need some desc right here.")
    @GetMapping("/api/v1/Vendor")
    Flux<Vendor> getList(){
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/Vendor/{id}")
    Mono<Vendor> getById(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @ApiOperation(value="Crete New Vendor", notes="You may need some desc right here.")
    @ApiImplicitParam(name = "Vendor", value = "Vendor cls", required = true, dataType = "Vendor")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/Vendor")
    Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorPublisher){
        return vendorRepository.saveAll(vendorPublisher).then();
    }

    @PutMapping("/api/v1/Vendor/{id}")
    Mono<Vendor> update(@PathVariable String id,@RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/api/v1/Vendor/{id}")
    Mono<Vendor> patch(@PathVariable String id,@RequestBody Vendor vendor){
        Vendor foundVendor = vendorRepository.findById(id).block();

        if (
                !Objects.equals(foundVendor.getFirstName(), vendor.getFirstName())
                || !Objects.equals(foundVendor.getLastName(), vendor.getLastName())
                ){
            foundVendor.setFirstName(vendor.getFirstName());
            foundVendor.setLastName(vendor.getLastName());
            return vendorRepository.save(vendor);
        }
        return Mono.just(foundVendor);
    }
}
