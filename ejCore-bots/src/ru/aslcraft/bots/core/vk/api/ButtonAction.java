package ru.aslcraft.bots.core.vk.api;

import lombok.Getter;
import lombok.Setter;

public class ButtonAction {
    @Getter
    @Setter
    String label;
    @Getter
    @Setter
    String type;
    @Getter
    @Setter
    String payload;
}
