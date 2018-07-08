package br.com.jhonatanserafim.bankslips.api.mapper;

import br.com.jhonatanserafim.bankslips.api.dto.BankslipDto;
import br.com.jhonatanserafim.bankslips.api.entity.Bankslip;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankslipMapper {

    @Autowired
    private ModelMapper modelMapper;

    public BankslipDto to(Bankslip entity) {
        return modelMapper.map(entity, BankslipDto.class);
    }

    public Bankslip to(BankslipDto dto) {
        return modelMapper.map(dto, Bankslip.class);
    }

    public List<BankslipDto> toList(List<Bankslip> list) {
        return list.stream().map(this::to).collect(Collectors.toList());
    }
}
