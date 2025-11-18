package com.tudai.arquitecturasweb.microserviciopago.service;

import com.tudai.arquitecturasweb.microserviciopago.dto.FacturacionDTO;
import com.tudai.arquitecturasweb.microserviciopago.entity.Pago;
import com.tudai.arquitecturasweb.microserviciopago.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@Service
public class PagoService {

    @Autowired
    PagoRepository pagoRepository;

    public List<Pago> getAll() {
        return pagoRepository.findAll();
    }

    public Pago getById(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void delete(Long id) {
        pagoRepository.deleteById(id);
    }

    public void update(Long id, Pago nuevo) {
        Pago p = pagoRepository.findById(id).orElseThrow(() -> new RuntimeException("pago no encontrado"));

        p.setMonto(nuevo.getMonto());
        p.setFecha(nuevo.getFecha());

        pagoRepository.save(p);
    }

    public FacturacionDTO getFacturacionByFecha(int anio, int desdeMes, int hastaMes) {
        return this.pagoRepository.getFacturacionByFecha(anio, desdeMes, hastaMes);
    }
}