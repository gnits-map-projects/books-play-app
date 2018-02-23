package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ImageStore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImageController extends Controller {

    private final ImageStore imageStore;

    @Inject
    public ImageController(ImageStore imageStore) {
        this.imageStore = imageStore;
    }

    public Result uploadImage() {

        Logger.info("uploadImage");


        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        if (null == body) {
            return badRequest("Not multipart request");
        }

        Logger.debug("Got body");


        Http.MultipartFormData.FilePart<File> image = body.getFile("image");
        if (image == null) {
            return badRequest("Missing image file in multi part request");
        }

        Logger.debug("Got image");

        Logger.debug("Content type: {}", image.getContentType());
        if (!image.getContentType().equals("image/png")) {
            return badRequest("Only png images are supported");
        }

        Logger.debug("Content-Type ok");

        final Path source = image.getFile().toPath();

        try {
            final String imageId = imageStore.storeImage(source);

            ObjectNode result = Json.newObject();
            //final String downloadUrl = routes.ImageController.downloadImage(imageId).absoluteURL(request());
            result.put("image", imageId);
            return ok(result);

        } catch (IOException e) {
            e.printStackTrace();
            return internalServerError("Failed to store uploaded file");
        }

    }

    public Result downloadImage(String id) {

        final File file = imageStore.getImage(id);

        if (null == file) {
            return notFound("Could not find file: " +  id);
        }

        return ok(file);
    }

    public Result deleteImage(String id) {
        try {

            if (!imageStore.deleteImage(id)) {
                return notFound();
            }

            return ok();

        } catch (IOException e) {
            Logger.error("Failed to delete image: " + e);
            return internalServerError();
        }

    }
}
