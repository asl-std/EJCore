package org.aslstd.api.ejcore.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class LoadedFiles {
    @Getter private byte[] data;

    @Getter private String pluginName;

    @Getter private String type;
}
