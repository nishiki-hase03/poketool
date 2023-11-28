package com.nhworks.poketool.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RentalPartyForm {

    @Pattern(regexp="[A-Z0-9]{6}", message = "IDは英数字6桁で入力してください")
    private String rentalId;

    @NotBlank(message = "紹介文を入力してください")
    @Size(max = 400, message = "紹介文は400文字以下で入力してください")
    private String introduce;
}
