package org.example.Exe3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ViaCepClientTest {

    private ViaCepClient mockClient;

    @BeforeEach
    void setup() {
        mockClient = mock(ViaCepClient.class);
    }

    // CEP com letra (inválido)
    @Test
    void testCepComLetraDeveRetornarErro() {
        when(mockClient.consultarCep("95010A10")).thenThrow(new IllegalArgumentException("Formato inválido"));
        assertThrows(IllegalArgumentException.class, () -> mockClient.consultarCep("95010A10"));
    }

    // CEP vazio (inválido)
    @Test
    void testCepVazioDeveRetornarErro() {
        when(mockClient.consultarCep("")).thenThrow(new IllegalArgumentException("Formato inválido"));
        assertThrows(IllegalArgumentException.class, () -> mockClient.consultarCep(""));
    }

    // CEP menos de 8 dígitos (inválido)
    @Test
    void testCepMenorQue8DigitosDeveRetornarErro() {
        when(mockClient.consultarCep("9501001")).thenThrow(new IllegalArgumentException("Formato inválido"));
        assertThrows(IllegalArgumentException.class, () -> mockClient.consultarCep("9501001"));
    }

    // CEP inexistente (retorna erro=true)
    @Test
    void testCepInexistenteDeveRetornarErroTrue() {
        EnderecoResponse respErro = new EnderecoResponse();
        respErro.setErro(true);
        when(mockClient.consultarCep("99999999")).thenReturn(respErro);
        assertTrue(mockClient.consultarCep("99999999").isErro());
    }
    // Consulta endereço com UF inválido
    @Test
    void testUfInvalidoDeveRetornarErro() {
        when(mockClient.consultarEndereco("XX", "Sao Paulo", "Avenida Paulista"))
                .thenThrow(new IllegalArgumentException("UF inválido"));
        assertThrows(IllegalArgumentException.class,
                () -> mockClient.consultarEndereco("XX", "Sao Paulo", "Avenida Paulista"));
    }
    // Consulta endereço com cidade menor que 3 caracteres
    @Test
    void testCidadeMenorQue3CaracteresDeveRetornarErro() {
        when(mockClient.consultarEndereco("SP", "Pa", "Avenida Paulista"))
                .thenThrow(new IllegalArgumentException("Cidade inválida"));
        assertThrows(IllegalArgumentException.class,
                () -> mockClient.consultarEndereco("SP", "Pa", "Avenida Paulista"));
    }
    // Consulta endereço com logradouro menor que 3 caracteres
    @Test
    void testLogradouroMenorQue3CaracteresDeveRetornarErro() {
        when(mockClient.consultarEndereco("SP", "Sao Paulo", "Av"))
                .thenThrow(new IllegalArgumentException("Logradouro inválido"));
        assertThrows(IllegalArgumentException.class,
                () -> mockClient.consultarEndereco("SP", "Sao Paulo", "Av"));
    }
    // Consulta endereço logradouro inexistente (lista vazia)
    @Test
    void testLogradouroInexistenteDeveRetornarListaVazia() {
        when(mockClient.consultarEndereco("SP", "Sao Paulo", "Fantasma")).thenReturn(Collections.emptyList());
        List<EnderecoResponse> lista = mockClient.consultarEndereco("SP", "Sao Paulo", "Fantasma");
        assertTrue(lista.isEmpty());
    }
    // Cidade com acento retorna lista não vazia
    @Test
    void testCidadeComAcentoDeveRetornarLista() {
        EnderecoResponse resp = new EnderecoResponse();
        resp.setErro(false);
        when(mockClient.consultarEndereco("SP", "São Paulo", "Avenida Paulista"))
                .thenReturn(List.of(resp));
        List<EnderecoResponse> lista = mockClient.consultarEndereco("SP", "São Paulo", "Avenida Paulista");
        assertFalse(lista.isEmpty());
    }
    // Cidade sem acento retorna lista não vazia
    @Test
    void testCidadeSemAcentoDeveRetornarLista() {
        EnderecoResponse resp = new EnderecoResponse();
        resp.setErro(false);
        when(mockClient.consultarEndereco("SP", "Sao Paulo", "Avenida Paulista"))
                .thenReturn(List.of(resp));
        List<EnderecoResponse> lista = mockClient.consultarEndereco("SP", "Sao Paulo", "Avenida Paulista");
        assertFalse(lista.isEmpty());
    }
}
