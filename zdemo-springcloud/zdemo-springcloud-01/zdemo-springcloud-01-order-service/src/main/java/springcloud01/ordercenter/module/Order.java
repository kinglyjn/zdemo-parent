package springcloud01.ordercenter.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author KJ
 * @Date 2020-04-01 11:50 PM
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@TableName("tb_order")
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String orderNumber;
    private BigDecimal price;
    private String img;
    private Date createTime;
    private Long userId;
    private String username;
    private Long productId;
}
