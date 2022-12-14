package com.vr.beneficios.miniautorizador.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.beneficios.miniautorizador.core.entity.Cartao;
import com.vr.beneficios.miniautorizador.core.mapper.CartaoMapper;
import com.vr.beneficios.miniautorizador.core.usecase.CartaoUseCase;
import com.vr.beneficios.miniautorizador.infrastructure.http.CartaoController;
import com.vr.beneficios.miniautorizador.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartaoControllerTest {

    @Mock
    CartaoUseCase cartaoUseCase;

    @Spy
    CartaoMapper cartaoMapper = Mappers.getMapper(CartaoMapper.class);

    @Autowired
    MockMvc mockMvc;

    private static final String PATH = "/cartoes";

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CartaoController(cartaoUseCase, cartaoMapper)).build();
    }

    @Test
    void criarCartaoSucesso() throws Exception {
        Cartao cartao = TestUtils.getCartao();
        Mockito.when(cartaoUseCase.salvar(cartao)).thenReturn(cartao);
        var request = MockMvcRequestBuilders.post(PATH).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(TestUtils.getCartaoDTO()));
        var result = this.mockMvc.perform(request).andExpect(status().isCreated()).andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.numeroCartao", is("123456789"))).andReturn();
        assertNotNull(result);
    }

    @Test
    void buscarSaldoSucesso() throws Exception {
        Cartao cartao = TestUtils.getCartao();
        Mockito.when(cartaoUseCase.getCartaoByNumero(Mockito.anyString())).thenReturn(cartao);
        var request = MockMvcRequestBuilders.get(PATH + "/numeroCartao");
        var result = this.mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty()).andReturn();
        assertEquals(result.getResponse().getContentAsString(), "500");
    }
}
