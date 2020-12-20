package ssm.learning.androidLearningTask.FinalTask.WebService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

        @SerializedName("Response")
        @Expose
        private String response;
        @SerializedName("Error")
        @Expose
        private String error;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

    }

