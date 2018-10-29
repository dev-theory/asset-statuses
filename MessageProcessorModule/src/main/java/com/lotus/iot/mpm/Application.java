package com.lotus.iot.mpm;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lotus.iot.mpm.api.objectmodel.request.SubscribeRequest;
import com.lotus.iot.mpm.api.objectmodel.response.AllAssetsInStateResponse;
import com.lotus.iot.mpm.api.objectmodel.response.AssetCurrentStatusResponse;
import com.lotus.iot.mpm.api.objectmodel.response.GeneralResponse;
import com.lotus.iot.mpm.eventhandler.AssetStatusChangeQueue;
import com.lotus.iot.mpm.eventhandler.EventHandlerFacade;
import com.lotus.iot.mpm.eventhandler.objectmodel.AssetStatusEvent;
import com.lotus.iot.mpm.eventhandler.objectmodel.SampleListener;
import com.lotus.iot.mpm.handler.MessageHandler;
import com.lotus.iot.mpm.objectmodel.AssetStatusMessage;
import com.lotus.iot.mpm.objectmodel.type.StatusType;
import com.lotus.iot.mpm.report.ReportHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Niloufar Mazloumpour
 * @mail niloufar@mazloumpour.net
 * @since 2018-05-22
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class);

        subscribeSomeListeners(applicationContext);

        if (args.length > 0) {
            File file = new File(args[0]);
            sendMessages(file, applicationContext);
        }

        waitForProcessToFinish(applicationContext);

        System.out.println();
        System.out.println("-------------------");
        System.out.println("Do some Reporting..");
        System.out.println("-------------------");
        System.out.println();

        currentStatusReport(applicationContext);

        assetsInStateReport(applicationContext);
    }

    private static void subscribeSomeListeners(ConfigurableApplicationContext applicationContext) {
        EventHandlerFacade eventHandlerFacade = applicationContext.getBean(EventHandlerFacade.class);
        List<String> rows = loadFromFile("subscribe.txt");
        for (String row : rows) {
            String[] subscribeInfo = row.split(",");
            try {
                AssetStatusEvent event = AssetStatusEvent.valueOf(subscribeInfo[1]);
                String assetId = subscribeInfo[0];
                SubscribeRequest subscribeRequest = new SubscribeRequest(assetId, event, new SampleListener());
                eventHandlerFacade.subscribe(subscribeRequest);
            } catch (IllegalArgumentException e) {
                System.out.println(String.format("AssetStatusEvent %s does not exist", subscribeInfo[1]));
            }
        }
    }

    private static void assetsInStateReport(ConfigurableApplicationContext applicationContext) {
        ReportHandler reportHandler = applicationContext.getBean(ReportHandler.class);
        List<String> statusTypeList = loadFromFile("all_assets_in_state_report.txt");
        for (String status : statusTypeList) {
            GeneralResponse response = reportHandler.assetsInState(status);
            try {
                AllAssetsInStateResponse allAssetsInStateResponse = (AllAssetsInStateResponse) response;
                List<String> assetIdList = allAssetsInStateResponse.getAssetIdList();
                System.out.println(String.format("\nThere are %s assets which currently have an %s status:", assetIdList.size(), status));
                assetIdList.forEach(id -> System.out.println("'" + id + "'"));
            } catch (ClassCastException e) {
                System.out.println(String.format("\nstatus: %s, %s", status, response.getMessage()));
            }
        }
    }

    private static void currentStatusReport(ConfigurableApplicationContext applicationContext) {
        ReportHandler reportHandler = applicationContext.getBean(ReportHandler.class);
        List<String> assetIdList = loadFromFile("asset_status_report.txt");
        for (String assetId : assetIdList) {
            GeneralResponse response = reportHandler.assetCurrentStatus(assetId);
            try {
                AssetCurrentStatusResponse assetCurrentStatusResponse = (AssetCurrentStatusResponse) response;
                StatusType currentStatus = assetCurrentStatusResponse.getStatusType();
                System.out.println(String.format("Current status for asset '%s' is '%s'", assetId, currentStatus));
            } catch (ClassCastException e) {
                System.out.println(String.format("assetId: %s, %s", assetId, response.getMessage()));

            }
        }
    }

    private static List<String> loadFromFile(String fileName) {
        try {
            Resource resource = new ClassPathResource("input/" + fileName);
            InputStream inputStream = resource.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Stream<String> lines = bufferedReader.lines();
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static void waitForProcessToFinish(ConfigurableApplicationContext applicationContext) {
        AssetStatusChangeQueue assetStatusChangeQueue = applicationContext.getBean(AssetStatusChangeQueue.class);

        while (assetStatusChangeQueue.size() > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendMessages(File file, ConfigurableApplicationContext applicationContext) {
        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(file);
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(fileReader);
            JsonArray jsonArray = (JsonArray) obj;
            for (JsonElement element : jsonArray) {
                AssetStatusMessage message = gson.fromJson(element, AssetStatusMessage.class);
                MessageHandler messageHandler = applicationContext.getBean(MessageHandler.class);
                messageHandler.assetStatus(message);
            }
        } catch (FileNotFoundException e) {
            System.out.println(String.format("file not found, %s", file));
        }
    }
}
