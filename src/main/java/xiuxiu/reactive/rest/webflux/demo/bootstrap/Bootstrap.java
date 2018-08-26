package xiuxiu.reactive.rest.webflux.demo.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xiuxiu.reactive.rest.webflux.demo.domain.Category;
import xiuxiu.reactive.rest.webflux.demo.domain.Vendor;
import xiuxiu.reactive.rest.webflux.demo.repository.CategoryRepository;
import xiuxiu.reactive.rest.webflux.demo.repository.VendorRepository;

@Component
public class Bootstrap implements CommandLineRunner {

    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    public Bootstrap(VendorRepository vendorRepository, CategoryRepository categoryRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Init data");

        if (vendorRepository.count().block() == 0 && categoryRepository.count().block() == 0){
            vendorRepository.save(Vendor.builder()
                    .firstName("Heng-Shiou")
                    .lastName("Sheu")
                    .build()
            ).block();

            vendorRepository.save(Vendor.builder()
            .firstName("Wang")
            .lastName("Hang")
            .build()).block();

            System.out.println("Vendor Created !");

            categoryRepository.save(Category.builder()
            .description("School")
            .build()).block();

            categoryRepository.save(Category.builder()
            .description("Club")
            .build()).block();

            System.out.println("Category created!");

            System.out.println("Vendor count:" + vendorRepository.count().block());
            System.out.println("Category count:" + categoryRepository.count().block());
        }
    }
}
