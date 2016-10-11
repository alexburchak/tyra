package org.alexburchak.tyra.controller;

import org.alexburchak.tyra.config.TyraConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author alexburchak
 */
public class TyraControllerTest extends AbstractTestNGSpringWebAppTests {
    @Autowired
    private TyraConfiguration tyraConfiguration;

    /*
    @Test
    public void testTyra() throws Exception {
        String sid = UUID.randomUUID().toString();

        String queueName = tyraConfiguration.getQueueNamePrefix() + sid;

        doAnswer(i -> {
            Queue queue = ((Queue) i.getArguments()[0]);

            assertEquals(queue.getName(), queueName);

            return queue.getName();
        }).when(amqpAdmin).declareQueue(any(Queue.class));

        doAnswer(i -> {
            Binding binding = ((Binding) i.getArguments()[0]);

            assertEquals(binding.getExchange(), rabbitConfiguration.getExchangeName());
            assertEquals(binding.getRoutingKey(), sid);

            return null;
        }).when(amqpAdmin).declareBinding(any(Binding.class));

        mockMvc
                .perform(MockMvcRequestBuilders.get(TyraController.PATH_TYRA)
                                .param(TyraController.PARAM_SID, sid)
                )
                .andExpect(status().isOk())
                .andExpect(view().name(TyraController.RESULT_SUCCESS));

        verify(amqpAdmin).declareQueue(any(Queue.class));
        verify(amqpAdmin).declareBinding(any(Binding.class));

        verifyNoMoreInteractions(amqpAdmin);
    }

    @Test
    public void testTyraNoSid() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get(TyraController.PATH_TYRA))
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(amqpAdmin);
    }
    */
}
