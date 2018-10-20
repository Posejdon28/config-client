package pl.kamiluchnast.configclient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class SapOrder {

    private String id;
    @JsonProperty("magento_id")
    private Long magentoId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public SapOrder(MagOrder magOrder) {
        this.id = "MAG" + magOrder.getId();
        this.magentoId = magOrder.getMagentoId();
        this.createdAt = magOrder.getCreatedAt();
    }
}
