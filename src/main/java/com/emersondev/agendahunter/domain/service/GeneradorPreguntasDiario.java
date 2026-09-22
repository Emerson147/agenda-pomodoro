package com.emersondev.agendahunter.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneradorPreguntasDiario {

    private static final List<String> BANCO_PRODUCTIVIDAD = List.of(
            "¿Qué logré hoy? (Celebra incluso las victorias más pequeñas)",
            "¿Cumplí con mis prioridades principales? Si no fue así, ¿qué me distrajo?",
            "¿Qué quedó pendiente? (Anótalo de una vez para mañana y sácalo de tu cabeza)",
            "¿Cómo gestioné mi tiempo hoy? ¿Hubo momentos de fuga de energía?"
    );

    private static final List<String> BANCO_BIENESTAR = List.of(
            "¿Cómo me siento justo ahora? (Define tu emoción actual en una palabra)",
            "¿Qué me estresó o me quitó la paz hoy? ¿Cómo lo manejé?",
            "¿Qué hice hoy por mi salud física o mental?",
            "¿Hubo algún momento del día en el que no fui fiel a mis valores?"
    );

    private static final List<String> BANCO_GRATITUD = List.of(
            "¿De qué tres cosas estoy agradecido hoy?",
            "¿Qué aprendí hoy? (De un error, de un libro o de otra persona)",
            "¿Quién me hizo sentir bien hoy? ¿Le di las gracias?",
            "Si pudiera repetir el día de hoy, ¿qué haría diferente?",
            "¿Cuál es mi prioridad número uno para mañana?",
            "¿Qué puedo hacer esta noche para que mi mañana sea más fácil?"
    );

    private final Random random = new Random();

    public List<String> generarPreguntas() {
        List<String> seleccionadas = new ArrayList<>();
        
        seleccionadas.add(seleccionarAlAzar(BANCO_PRODUCTIVIDAD));
        seleccionadas.add(seleccionarAlAzar(BANCO_BIENESTAR));
        seleccionadas.add(seleccionarAlAzar(BANCO_GRATITUD));
        
        return seleccionadas;
    }

    private String seleccionarAlAzar(List<String> banco) {
        int index = random.nextInt(banco.size());
        return banco.get(index);
    }
}
