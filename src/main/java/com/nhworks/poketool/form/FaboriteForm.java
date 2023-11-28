package com.nhworks.poketool.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FaboriteForm {

    String rentalId;

    @NotBlank(message = "メモを入力してください")
    @Size(max = 400, message = "メモは400文字以下で入力してください")
    String memo;
}
