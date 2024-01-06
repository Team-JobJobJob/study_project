package team01.studyCm.util;

import lombok.experimental.UtilityClass;
import team01.studyCm.user.entity.status.Job;

@UtilityClass
public class EnumUtility {
    public String jobEnumValueToName(String value) {
        for (Job job : Job.values()) {
            if(job.getKey().equals(value)){
                return job.name();
            }
        }
        return "error";
    }
}
