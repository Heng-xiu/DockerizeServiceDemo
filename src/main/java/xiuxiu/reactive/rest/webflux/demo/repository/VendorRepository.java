package xiuxiu.reactive.rest.webflux.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import xiuxiu.reactive.rest.webflux.demo.domain.Vendor;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String>{
}
