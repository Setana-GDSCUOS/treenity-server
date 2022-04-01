package org.setana.treenity.repository.sql;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

@Component
public class QueryProps {

    private Map<String, String> props = new HashMap<>();

    public QueryProps() {
        try {
            ClassPathResource resource = new ClassPathResource("query.yml");
            props = new Yaml().load(new InputStreamReader(resource.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(QueryKey queryKey) {
        return props.get(queryKey.name());
    }

}
