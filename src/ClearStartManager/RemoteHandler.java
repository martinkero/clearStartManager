package ClearStartManager;

import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class RemoteHandler {
    static String remoteServerUrl = "http://clearstart.clearit.se/"; //TODO: Set dynamically

}

class Response {
    String code;
    String status;
    JsonObject data;
}

class Request {
    String queryString;
    private String baseUrl = RemoteHandler.remoteServerUrl;
    private String clientType = CustomerHandler.clientType;
    private String action;

    Request(String action) {
        this.action = action;
        this.queryString = "?type=" + this.clientType + "&action=" + this.action;
    }

    String sendGetRequest() throws IOException {
        BufferedInputStream inputStream = null;
        try {
            URL url = new URL(this.baseUrl + this.queryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            inputStream = new BufferedInputStream(connection.getInputStream());
            byte[] contents = new byte[1024];
            int bytesRead;
            StringBuilder responseBody = new StringBuilder();
            while ((bytesRead = inputStream.read(contents)) != -1) {
                responseBody.append(new String(contents, 0, bytesRead));
            }
            return responseBody.toString();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    String sendPostRequest(String dataBody) throws IOException {
        BufferedInputStream inputStream = null;
        try {
            URL url = new URL(this.baseUrl + this.queryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(dataBody);
            outputStream.flush();
            outputStream.close();

            inputStream = new BufferedInputStream(connection.getInputStream());
            byte[] contents = new byte[1024];
            int bytesRead;
            StringBuilder responseBody = new StringBuilder();
            while ((bytesRead = inputStream.read(contents)) != -1) {
                responseBody.append(new String(contents, 0, bytesRead));
            }
            return responseBody.toString();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}

class GetRequest extends Request {
    GetRequest() {
        super("get");
    }

    GetRequest(String customerName) {
        super("get");
        this.queryString += "&name=" + customerName;
    }
}

class CreateRequest extends Request {
    private String installationParameters;

    CreateRequest(String customerName, String installationParameters) {
        super("create");
        this.installationParameters = installationParameters;
        this.queryString += "&name=" + customerName;
    }

    String sendCreateRequest() throws IOException {
        return sendPostRequest(this.installationParameters);
    }
}

class ModifyRequest extends Request {
    private String installationParameters;

    ModifyRequest(String customerName, String installationParameters) {
        super("modify");
        this.installationParameters = installationParameters;
        this.queryString += "&name=" + customerName;
    }

    String sendModifyRequest() throws IOException {
        return sendPostRequest(this.installationParameters);
    }
}

class DeleteRequest extends Request {
    DeleteRequest(String customerName) {
        super("delete");
        this.queryString += "&name=" + customerName;
    }

    String sendDeleteRequest() throws IOException {
        return sendGetRequest();
    }
}

