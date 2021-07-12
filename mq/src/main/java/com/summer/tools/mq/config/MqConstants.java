package com.summer.tools.mq.config;

public interface MqConstants {
    // rabbit exchange
    String DIRECT_EXCHANGE = "directExchange";
    String TOPIC_EXCHANGE = "topicExchange";

    // rabbit mqName
    String DIRECT_QUEUE = "directQueue";
    String DEFAULT_DIRECT_QUEUE = "defaultDirectQueue";

    String MAN = "topic.man";
    String WOMAN = "topic.woman";

    // rabbit binding
    String DIRECT_ROUTING = "directRouting";
    String TOPIC_ROUTING = "topic.#";
}
