package com.lesconstructionssapete.stempyerp.service.impl.constant;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.domain.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.domain.constant.TaxGroup;
import com.lesconstructionssapete.stempyerp.domain.constant.TaxGroupLine;
import com.lesconstructionssapete.stempyerp.domain.constant.TaxRate;
import com.lesconstructionssapete.stempyerp.domain.repository.ConstantRepository;
import com.lesconstructionssapete.stempyerp.port.persistence.SQLConnectionProvider;
import com.lesconstructionssapete.stempyerp.service.spi.constant.ConstantService;

public class ConstantServiceImpl implements ConstantService {

  private final SQLConnectionProvider provider;
  private final ConstantRepository constantRepository;

  public ConstantServiceImpl(SQLConnectionProvider provider, ConstantRepository constantRepository) {
    this.provider = provider;
    this.constantRepository = constantRepository;
  }

  @Override
  public List<DomainEntityType> getDomainEntityTypes() {
    try (Connection conn = provider.getConnection()) {
      return constantRepository.getEntityTypes(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Entity Types", e);
    }
  }

  @Override
  public List<RetailCategory> getRetailCategories() {
    try (Connection conn = provider.getConnection()) {
      return constantRepository.getRetailCategories(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Retail Categories", e);
    }
  }

  @Override
  public List<PaymentMethod> getPaymentMethods() {
    try (Connection conn = provider.getConnection()) {
      return constantRepository.getPaymentMethods(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Payment Methods", e);
    }

  }

  @Override
  public List<TaxGroup> getTaxGroups() {
    try (Connection conn = provider.getConnection()) {
      // Fetch all tax rates, group lines, and groups first to minimize database calls
      List<TaxRate> taxRates = constantRepository.getTaxRates(conn);
      List<TaxGroupLine> taxGroupLines = constantRepository.getTaxGroupLines(conn);
      List<TaxGroup> taxGroups = constantRepository.getTaxGroups(conn);

      for (TaxGroup group : taxGroups) {
        // Find all lines for this group
        List<TaxGroupLine> lines = taxGroupLines.stream()
            .filter(line -> line.getTaxGroupId() == group.getId())
            .toList();

        for (TaxGroupLine line : lines) {
          // Find the corresponding TaxRate for this line
          TaxRate rate = taxRates.stream()
              .filter(r -> r.getId() == line.getTaxRateId())
              .findFirst()
              .orElseThrow(() -> new RuntimeException(
                  "Tax Rate with ID " + line.getTaxRateId() + " not found"));
          line.setTaxRate(rate);

        }
        // Separate active and inactive rates based on current date and enabled status
        lines.forEach((line) -> {
          LocalDate today = LocalDate.now();

          if (line.getTaxRate().isEnabled()
              && !line.getTaxRate().getValidFrom().isAfter(today) // validFrom <= today
              && (line.getTaxRate().getValidTo() == null || !line.getTaxRate().getValidTo().isBefore(today))) {
            group.getRates().add(line);
          } else {
            group.getInnactiveRates().add(line.getTaxRate());
          }
        });
      }

      return taxGroups;
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Tax Groups", e);
    }
  }

}
