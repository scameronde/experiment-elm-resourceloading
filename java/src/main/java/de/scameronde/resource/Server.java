package de.scameronde.resource;

import static de.scameronde.resource.JsonUtils.dataToJson;
import static de.scameronde.resource.JsonUtils.jsonToData;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.webSocket;

import java.util.List;
import java.util.Optional;

import javaslang.control.Either;
import spark.Request;
import spark.Response;

public class Server {

  public static void main(String[] args) {
    // this is only necessary for cross site development
    options("/*", ((request, response) -> allowCrossOriginRequests(response)));

    // large test json
    get("/resource", (((request, response) -> {
      return createResponse(response, Either.right(ResourceFile.JSON_TEXT));
    })));
  }

  private static Object allowCrossOriginRequests(Response response) {
    response.header("Access-Control-Allow-Origin", "*");
    response.header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
    response.header("Access-Control-Allow-Credentials", "false");
    response.header("Access-Control-Max-Age", "86400"); // 24 hours
    response.header("Access-Control-Allow-Headers", "X-Requested-With, X-HTTP-Method-Override, Content-Type, Accept");

    response.status(204);
    return "";
  }

  private static String createResponse(Response response, Either<Exception, String> payload) {
    if (payload.isRight()) {
      String payloadData = payload.get();
      setResponseOK(response);
      return payloadData;
    }
    else {
      setResponseError(response);
      return payload.left().get().getMessage();
    }
  }

  private static void setResponseError(Response response) {
    response.status(500);
    response.type("application/text");
    response.header("Access-Control-Allow-Origin", "*");
  }

  private static void setResponseOK(Response response) {
    response.status(200);
    response.type("application/json");
    response.header("Access-Control-Allow-Origin", "*");
  }

  private static void setResponseNotFound(Response response) {
    response.status(404);
    response.type("application/text");
    response.header("Access-Control-Allow-Origin", "*");
  }

  static String getParameter(Request request, String parameter) {
    return request.params(parameter);
  }
}
