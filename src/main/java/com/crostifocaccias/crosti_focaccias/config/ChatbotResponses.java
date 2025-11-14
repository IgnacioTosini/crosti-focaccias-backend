package com.crostifocaccias.crosti_focaccias.config;

import java.util.regex.Pattern;

/**
 * Configuración centralizada de respuestas del chatbot
 * Contiene todos los patrones de detección y sus respuestas asociadas
 */
public class ChatbotResponses {

    /**
     * Clase interna para agrupar patrón y respuesta
     */
    public static class ResponsePattern {
        private final Pattern pattern;
        private final String response;
        private final String category;

        public ResponsePattern(String regex, String response, String category) {
            this.pattern = Pattern.compile(regex);
            this.response = response;
            this.category = category;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public String getResponse() {
            return response;
        }

        public String getCategory() {
            return category;
        }
    }

    // ========== PATRONES Y RESPUESTAS ==========

    public static final ResponsePattern MENU = new ResponsePattern(
        "\\b(que|cuales|cuantas).*(focaccia|tienen|menu|productos|opciones)\\b",
        "", // Se genera dinámicamente desde la BD
        "MENU"
    );

    public static final ResponsePattern GREETING = new ResponsePattern(
        "\\b(hola|buenos|buenas|hey|ey)\\b",
        "¡Hola! 👋 Bienvenido a Crosti Focaccias. Soy tu asistente virtual y estoy aquí para ayudarte. ¿Qué te gustaría saber sobre nuestras focaccias artesanales? 🍕",
        "SALUDO"
    );

    public static final ResponsePattern VEGAN = new ResponsePattern(
        "\\b(vegan[ao]?s?|vegetarian[ao]?s?|sin carne|veggie|opciones.*(vegan|vegeta))\\b",
        "¡Sí! Tenemos deliciosas opciones veganas 🌱. En nuestro menú están identificadas con un ícono especial. ¿Te gustaría que te recomiende alguna?",
        "VEGANAS"
    );

    public static final ResponsePattern ORDER = new ResponsePattern(
        "\\b(pedido|comprar|pedir|orden|como.*(pido|compro))\\b",
        "Para hacer un pedido es muy fácil:\n1️⃣ Navega por nuestro menú\n2️⃣ Selecciona las focaccias que quieras\n3️⃣ Agrégalas al carrito\n4️⃣ Completa el checkout\n\n¡Listo! 🛒✨",
        "PEDIDOS"
    );

    public static final ResponsePattern DELIVERY = new ResponsePattern(
        "\\b(delivery|entrega|envio|domicilio|llevan)\\b",
        "¡Sí, hacemos delivery! 🚚 Puedes hacer tu pedido online a través de nuestro sitio web y te lo llevamos directo a tu casa. ¿Necesitas ayuda con algo más?",
        "DELIVERY"
    );

    public static final ResponsePattern PRICE = new ResponsePattern(
        "\\b(precio|cuesta|cuanto|valor|sale)\\b",
        "Nuestras focaccias tienen precios desde $3000. Te muestro el menú completo con todos los precios en la sección de productos. ¿Te gustaría saber algo más? 🍕💰",
        "PRECIOS"
    );

    public static final ResponsePattern INGREDIENTS = new ResponsePattern(
        "\\b(ingredientes?|lleva|contiene|hecho|composicion|usan|usa|utilizan)\\b",
        "Todas nuestras focaccias están hechas con ingredientes frescos y de calidad 🌿. Cada una tiene una descripción detallada en el menú donde puedes ver exactamente qué lleva. ¿Hay alguna en particular que te interese?",
        "INGREDIENTES"
    );

    public static final ResponsePattern HOURS = new ResponsePattern(
        "\\b(horario|abierto|abren|cierran|hora|cuando)\\b",
        "Estamos disponibles para pedidos online 24/7 🕐. Los pedidos se procesan y entregan de Lunes a Domingo. ¿Querés hacer un pedido?",
        "HORARIOS"
    );

    public static final ResponsePattern LOCATION = new ResponsePattern(
        "\\b(donde|ubicacion|direccion|zona|lugar)\\b",
        "Somos una tienda online de focaccias artesanales en Argentina 🇦🇷. Hacemos delivery a toda la zona. ¿Querés hacer un pedido?",
        "UBICACION"
    );

    public static final ResponsePattern HELP = new ResponsePattern(
        "\\b(ayuda|ayudar|consulta|info|informacion)\\b",
        "¡Claro! Estoy para ayudarte 😊. Puedo contarte sobre:\n\n🍕 Nuestras focaccias y precios\n🌱 Opciones veganas\n🛒 Cómo hacer un pedido\n🚚 Delivery\n\n¿Sobre qué te gustaría saber más?",
        "AYUDA"
    );

    public static final ResponsePattern GOODBYE = new ResponsePattern(
        "\\b(gracias|chau|adios|hasta|bye)\\b",
        "¡De nada! Gracias por visitar Crosti Focaccias 🍕. ¡Esperamos tu pedido! Que tengas un excelente día 😊",
        "DESPEDIDA"
    );

    public static final String DEFAULT_RESPONSE = 
        "No estoy seguro de entender tu pregunta 🤔. ¿Te refieres a alguna de estas opciones?\n\n" +
        "🍕 ¿Qué focaccias tienen?\n" +
        "🌱 ¿Tienen opciones veganas?\n" +
        "💰 ¿Cuánto cuesta?\n" +
        "🛒 ¿Cómo hacer un pedido?\n" +
        "🚚 ¿Hacen delivery?\n" +
        "🌿 ¿Qué ingredientes usan?\n" +
        "🕐 ¿Cuál es el horario?\n" +
        "📍 ¿Dónde están ubicados?";

    public static final String ERROR_RESPONSE = 
        "Lo siento, tuve un pequeño problema. ¿Podrías seleccionar una de las opciones sugeridas? 😊";

    public static final String MENU_EMPTY_RESPONSE = 
        "Por el momento estamos actualizando nuestro menú. Por favor, vuelve pronto para ver nuestras deliciosas focaccias 🍕";

    public static final String MENU_ERROR_RESPONSE = 
        "Puedes ver todo nuestro menú en la sección de productos. ¡Tenemos opciones deliciosas para todos los gustos! 🍕";

    /**
     * Array de patrones ordenados por prioridad (de más específico a más general)
     */
    public static final ResponsePattern[] PATTERNS_BY_PRIORITY = {
        MENU,       // Más específico primero
        VEGAN,
        ORDER,
        DELIVERY,
        PRICE,
        INGREDIENTS,
        HOURS,
        LOCATION,
        HELP,
        GREETING,   // Más general
        GOODBYE
    };
}
