package xiuxiu.reactive.rest.webflux.demo.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xiuxiu.reactive.rest.webflux.demo.domain.Vendor;
import xiuxiu.reactive.rest.webflux.demo.repository.VendorRepository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class VendorRestControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorRestController vendorRestController;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorRestController = new VendorRestController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorRestController).build();
    }

    @Test
    public void getList() throws Exception {
        given(vendorRepository.findAll())
                .willReturn(
                        Flux.just(
                                Vendor.builder().firstName("Wang").lastName("Ku").build(),
                                Vendor.builder().firstName("Amy").lastName("Saman").build()
                        )
                );

        webTestClient.get()
                .uri("/api/v1/Vendor")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() throws Exception {
        given(vendorRepository.findById("someId"))
                .willReturn(
                        Mono.just(Vendor.builder().firstName("Wang").lastName("Ku").build())
                );

        webTestClient.get()
                .uri("/api/v1/Vendor/someId")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void createVendor() throws Exception{
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> JihungToCreateVendor = Mono.just(
                Vendor.builder()
                        .firstName("Jihung")
                        .lastName("Chen")
                        .build()
        );

        webTestClient.post()
                .uri("/api/v1/Vendor")
                .body(JihungToCreateVendor, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateVendor() throws Exception{
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(
                Vendor.builder()
                        .firstName("Jihung")
                        .lastName("Chen")
                        .build()
        );

        webTestClient.put()
                .uri("/api/v1/Vendor/putUrIdHere")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void patchVendor() throws Exception{

        given(vendorRepository.findById(anyString()))
        .willReturn(Mono.just(Vendor.builder().build()));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(
                Vendor.builder()
                        .firstName("WWW")
                        .lastName("MMM")
                        .build()
        );

        webTestClient.patch()
                .uri("/api/v1/Vendor/putUrIdHere")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository).save(any());
    }

    @Test
    public void patchVendorWithoutChange() throws Exception{

        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().firstName("WWW")
                        .lastName("MMM")
                        .build()
                ));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMono = Mono.just(
                Vendor.builder()
                        .firstName("WWW")
                        .lastName("MMM")
                        .build()
        );

        webTestClient.patch()
                .uri("/api/v1/Vendor/putUrIdHere")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository, never()).save(any());

    }
}