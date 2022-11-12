package org.aslstd.api.ejcore.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CallableFileData {
    @Getter private String pluginName;

    @Getter private boolean saved;

    @Getter private String type;
}
