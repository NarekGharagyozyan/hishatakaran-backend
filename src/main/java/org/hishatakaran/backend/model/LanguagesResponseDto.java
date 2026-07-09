package org.hishatakaran.backend.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LanguagesResponseDto{

  private String hy;
  private String en;
  private String fr;


  public static LanguagesResponseDto of(
      String hy,
      String en,
      String fr
  ) {

    if (isEmpty(hy) && isEmpty(en) && isEmpty(fr)) {
      return null;
    }

    return new LanguagesResponseDto(
        normalize(hy),
        normalize(en),
        normalize(fr)
    );
  }


  private static boolean isEmpty(String value) {

    return value == null
        || value.isBlank()
        || value.equalsIgnoreCase("null");
  }


  private static String normalize(String value) {

    if (value == null) {
      return null;
    }

    if (value.equalsIgnoreCase("null")) {
      return null;
    }

    return value;
  }
}
