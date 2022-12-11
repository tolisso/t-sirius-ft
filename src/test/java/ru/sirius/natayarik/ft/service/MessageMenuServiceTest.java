package ru.sirius.natayarik.ft.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.sirius.natayarik.ft.services.MessageMenuService;

import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties= {"spring.autoconfigure.exclude=org.telegram.telegrambots.starter.TelegramBotStarterConfiguration"})
public class MessageMenuServiceTest {
    private final MessageMenuService messageMenuService;

    public MessageMenuServiceTest() {
        this.messageMenuService = new MessageMenuService();
    }

    @Test
    public void testGetInlineMenuMessage() {
        SendMessage message = messageMenuService.getInlineMenuMessage(1, "message", Map.of());

        Assertions.assertEquals("1", message.getChatId());
        Assertions.assertEquals("message", message.getText());
        Assertions.assertEquals("Markdown", message.getParseMode());
    }

    @Test
    public void testGetWithoutMenuMessage() {
        SendMessage message = messageMenuService.getWithoutMenuMessage(1, "message");

        Assertions.assertEquals("1", message.getChatId());
        Assertions.assertEquals("message", message.getText());
        Assertions.assertEquals("Markdown", message.getParseMode());
    }

    @Test
    public void testMainMenuMessage() {
        SendMessage message = messageMenuService.getMainMenuMessage(1, "message");

        Assertions.assertEquals("1", message.getChatId());
        Assertions.assertEquals("message", message.getText());
        Assertions.assertEquals("HTML", message.getParseMode());
    }

    @Test
    public void testGetNotChoseAccountMessage() {
        SendMessage askClickMessage = messageMenuService.getNotChoseAccountMessage(1);

        Assertions.assertEquals(
                "Функция недоступна, так как сейчас у вас нет кошельков. " +
                        "Чтобы создать новый, нажмите в главном меню на 'Сменить кошелек'",
                askClickMessage.getText()
        );
    }


}
