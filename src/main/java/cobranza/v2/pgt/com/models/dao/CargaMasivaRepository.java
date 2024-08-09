package cobranza.v2.pgt.com.models.dao;

import cobranza.v2.pgt.com.models.entity.CargaMasiva;
import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CargaMasivaRepository extends JpaRepository<CargaMasiva, Long>, JpaSpecificationExecutor<CargaMasiva> {

    Page<CargaMasiva> findAllByEstado(EstadoCargaMasiva estado, Pageable pageable);
    List<CargaMasiva> findAllByEstadoAndFechaAltaBetweenOrderByIdCargaMasiva(EstadoCargaMasiva estado, Date fechaInicial, Date fechaFinal);
    List<CargaMasiva> findAllByFechaAltaBetweenOrderByIdCargaMasiva(Date fechaInicial, Date fechaFinal);

    @Query(value = "Select distinct c.* from cam.carga_masiva c inner join cam.carga_masiva_detalle d on c.id_carga_masiva = d.id_carga_masiva where c.estado = :estado and LOWER(CONCAT( c.numero_cuenta, ' ', c.numero_cliente, ' ', d.numero_documento, ' ', d.nombre_beneficiario, ' ', d.periodo_abono, ' ', d.monto, ' ',  d.numero_cuenta, ' ', d.referencia, ' ', d.concepto)) LIKE %:search%",
            countQuery = "Select count(distinct c.*) from cam.carga_masiva_detalle d inner join cam.carga_masiva c on c.id_carga_masiva = d.id_carga_masiva where c.estado = :estado and LOWER(CONCAT( c.numero_cuenta, ' ', c.numero_cliente, ' ', d.numero_documento, ' ', d.nombre_beneficiario, ' ', d.periodo_abono, ' ', d.monto, ' ',  d.numero_cuenta, ' ', d.referencia, ' ', d.concepto)) LIKE %:search%",
            nativeQuery = true)
    Page<CargaMasiva> find(@Param("search") String search, @Param("estado") String estado, Pageable pageable);
}