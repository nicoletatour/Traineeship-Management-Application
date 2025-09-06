package myproject.traineeship_management_app.services;

import myproject.traineeship_management_app.domainmodel.Company;
import myproject.traineeship_management_app.domainmodel.User;
import java.util.List;

public interface CompanyService {
    Company saveCompany(Company company);
    Company getById(Long id);
    Company getByUser(User user);
    List<Company> getAll();
    void deleteById(Long id);
}
