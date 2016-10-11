package org.alexburchak.tyra.controller;

/**
 * @author alexburchak
 */
public class HookControllerTest extends AbstractTestNGSpringWebAppTests {
    /*
    @Test
    public void testHook() throws Exception {
        String sid = UUID.randomUUID().toString();

        doAnswer(i -> {
            HttpRequest request = (HttpRequest) i.getArguments()[1];
            assertNotNull(request);

            return null;
        }).when(rabbitTemplate).convertAndSend(eq(sid), any(HttpRequest.class));

        mockMvc
                .perform(MockMvcRequestBuilders.get(HookController.PATH_HOOK)
                                .param(TyraController.PARAM_SID, sid)
                )
                .andExpect(status().isOk());

        verify(rabbitTemplate).convertAndSend(eq(sid), any(HttpRequest.class));
    }

    @Test
    public void testHookNoSid() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get(HookController.PATH_HOOK))
                .andExpect(status().isBadRequest());
    }
    */
}
