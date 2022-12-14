package com.vr.beneficios.miniautorizador.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.beneficios.miniautorizador.core.entity.Transacao;
import com.vr.beneficios.miniautorizador.core.mapper.TransacaoMapper;
import com.vr.beneficios.miniautorizador.core.usecase.TransacaoUseCase;
import com.vr.beneficios.miniautorizador.infrastructure.http.TransacaoController;
import com.vr.beneficios.miniautorizador.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransacaoControllerTest {

    @Mock
    TransacaoUseCase transacaoUseCase;

    @Spy
    TransacaoMapper transacaoMapper = Mappers.getMapper(TransacaoMapper.class);

    @Autowired
    MockMvc mockMvc;

    private static final String PATH = "/transacoes";

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TransacaoController(transacaoUseCase, transacaoMapper)).build();
    }

    @Test
    void transacaoSucesso() throws Exception {
        Transacao transacao = TestUtils.getTransacao();
        Mockito.when(transacaoUseCase.realizarTransacao(transacao)).thenReturn(TestUtils.getCartao());
        var request = MockMvcRequestBuilders.post(PATH).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(TestUtils.getTransacaoDTO()));
        var result = this.mockMvc.perform(request).andExpect(status().isCreated()).andExpect(jsonPath("$").isNotEmpty()).andReturn();
        assertEquals(result.getResponse().getContentAsString(), "OK");
    }

}
