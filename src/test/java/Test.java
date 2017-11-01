import lombok.val;
import pw.phylame.commons.value.Value;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) throws Exception {
        val now = Value.of(LocalDate::now).map(date -> date.plusDays(5));
        System.out.println(now.get());
    }
}
