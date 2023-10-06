package br.gov.mg.uberlandia.decserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DataDAO {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Date getDataHoraAtualBanco() {
        String sql = "SELECT SYSDATE FROM DUAL";
        Map<String, Object> parameters = new HashMap<>();

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Date.class);
    }

    public Date getDataAtualBanco() {
        String sql = "SELECT TRUNC(SYSDATE) FROM DUAL";
        Map<String, Object> parameters = new HashMap<>();

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Date.class);
    }

    public Date getDataAtualMaisDiasDoParametro(Long qtdDias) {
        String sql = "SELECT TRUNC(SYSDATE) + :qtdDias FROM DUAL";
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("qtdDias", qtdDias);


        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Date.class);
    }

    public Date getDataAtualMenosDiasDoParametro(Long qtdDias) {
        String sql = "SELECT TRUNC(SYSDATE - :qtdDias) FROM DUAL";
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("qtdDias", qtdDias);


        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Date.class);
    }


    public String getAnoCorrente() {
        String sql = "SELECT TO_CHAR(SYSDATE, 'yyyy') FROM DUAL";
        Map<String, Object> parameters = new HashMap<>();

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, String.class);
    }
}
