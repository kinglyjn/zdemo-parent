package springcloud01.usercenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author KJ
 * @Date 2020-04-01 6:23 PM
 * @Description
 */
@Data
@ApiModel("用户VO")
public class UserVo {

    @ApiModelProperty("用户名")
    private String username;

}
