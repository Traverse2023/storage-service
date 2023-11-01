// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
// import software.amazon.awssdk.services.sqs.SqsClient;

// @Configuration
// public class AwsSqsConfig {

//     @Autowired
//     private SqsClient sqsClient; // Inject the SqsClient

//     @Bean
//     public QueueMessagingTemplate queueMessagingTemplate() {
//         return new QueueMessagingTemplate(sqsClient);
//     }
// }