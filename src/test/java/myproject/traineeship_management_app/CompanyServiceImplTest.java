package myproject.traineeship_management_app;


import myproject.traineeship_management_app.domainmodel.Company;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.mappers.CompanyMapper;
import myproject.traineeship_management_app.services.CompanyServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock  private CompanyMapper companyRepo;
    @InjectMocks private CompanyServiceImpl service;


    @Test
    void saveCompany_persistsEntity() {
        Company c = new Company(); c.setId(2L);
        when(companyRepo.save(c)).thenReturn(c);

        Company saved = service.saveCompany(c);

        assertThat(saved).isSameAs(c);
        verify(companyRepo).save(c);
    }



    @Test
    void findByUser_returnsCompany() {
        User u = new User(); u.setUsername("comp1");
        Company c = new Company(); c.setUser(u);

        when(companyRepo.findByUser(u)).thenReturn(Optional.of(c));

        Company found = service.getByUser(u);

        assertThat(found).isSameAs(c);
    }

    @Test
    void findById_throws_whenMissing() {
        when(companyRepo.findById(123L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                     () -> service.getById(123L));
    }
}
