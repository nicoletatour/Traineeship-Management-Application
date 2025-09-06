package myproject.traineeship_management_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import myproject.traineeship_management_app.domainmodel.Company;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.mappers.CompanyMapper;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyMapper companyRepo;

    @Autowired
    public CompanyServiceImpl(CompanyMapper companyRepo) {
        this.companyRepo = companyRepo;
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepo.save(company);
    }

    @Override
    public Company getById(Long id) {
        return companyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No company with id " + id));
    }

    @Override
    public Company getByUser(User user) {
        return companyRepo.findByUser(user)
                .orElse(null);
    }

    @Override
    public List<Company> getAll() {
        return companyRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        companyRepo.deleteById(id);
    }
}

