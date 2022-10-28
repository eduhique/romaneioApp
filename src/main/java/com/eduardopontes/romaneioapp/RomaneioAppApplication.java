package com.eduardopontes.romaneioapp;

import com.eduardopontes.romaneioapp.model.client.Client;
import com.eduardopontes.romaneioapp.model.client.ClientType;
import com.eduardopontes.romaneioapp.model.product.Product;
import com.eduardopontes.romaneioapp.model.product.ProductConversionType;
import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import com.eduardopontes.romaneioapp.model.product.ProductType;
import com.eduardopontes.romaneioapp.model.user.Function;
import com.eduardopontes.romaneioapp.model.user.User;
import com.eduardopontes.romaneioapp.repository.ClientRepository;
import com.eduardopontes.romaneioapp.repository.ProductConversionTypeRepository;
import com.eduardopontes.romaneioapp.repository.ProductPrimitiveTypeRepository;
import com.eduardopontes.romaneioapp.repository.ProductRepository;
import com.eduardopontes.romaneioapp.repository.ProductTypeRepository;
import com.eduardopontes.romaneioapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class RomaneioAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RomaneioAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner test(UserRepository userRepository, ClientRepository clientRepository,
            ProductRepository productRepository, ProductPrimitiveTypeRepository productPrimitiveTypeRepository,
            ProductConversionTypeRepository productConversionTypeRepository,
            ProductTypeRepository productTypeRepository) {
        return args -> {
            var user = new User();
            user.setName("teste");
            user.setFunction(Function.ADMINISTRADOR);
            user.setNickname("testeUndo");
            user.setPassword("123456");

            var cliente = new Client();

            cliente.setName("cleunte");
            cliente.setClientType(ClientType.Varejo);

            var primitive = new ProductPrimitiveType();
            primitive.setLongName("Kilo");
            primitive.setFloat(true);
            primitive.setShortName("kg");

            productPrimitiveTypeRepository.save(primitive);

            var type = new ProductType();
            type.setLongName("Contentor");
            type.setShortName("cx");
            type.setFloat(false);

            productTypeRepository.save(type);

            var conversion = new ProductConversionType();
            conversion.setFromPrimary(1);
            conversion.setToTarget(20);
            conversion.setProductType(type);
            conversion.setTargetProductPrimitiveType(primitive);

            productConversionTypeRepository.save(conversion);

            type.setProductConversionTypes(Set.of(conversion));
            productTypeRepository.save(type);


            var product = new Product();
            product.setName("Uva Italia");
            product.setProductType(type);


            userRepository.save(user);
            clientRepository.save(cliente);
            productRepository.save(product);
        };
    }

}
