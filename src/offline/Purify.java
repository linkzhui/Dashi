package offline;
import java.io.BufferedReader;
import java.io.FileReader;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import static com.mongodb.client.model.Filters.eq;

import db.DBUtil;
public class Purify {
	public static void main(String[] args) {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(DBUtil.DB_NAME);
		// The name of the file to open.
		// Windows is different : C:\\Documents\\ratings_Musical_Instruments.csv
		String fileName = "/Users/raymon/Desktop/laiproject/ratings_Musical_Instruments.csv";

		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				String[] values = line.split(",");

				/*
				 * UpdateOptions options = new UpdateOptions().upsert(true);
 
				db.getCollection("restaurants").updateOne(
						new Document().append("business_id", businessId),
						new Document("$set",
							new Document()
									.append("business_id", businessId)
									.append("name", name)
									.append("categories", categories)
									.append("city", city).append("state", state)
									.append("full_address", fullAddress)
									.append("stars", stars)
									.append("latitude", latitude)
									.append("longitude", longitude)
									.append("image_url", imageUrl)
									.append("url", url)),
						options);
				list.add(obj);
			}
				 */
				UpdateOptions options = new UpdateOptions().upsert(true);
				db.getCollection("ratings").updateOne(eq("user",values[0]),
								new Document("$set", new Document()
										.append("user", values[0])
										.append("item", values[1])
										.append("rating",
												Double.parseDouble(values[2]))),options);

			}
			System.out.println("Import Done!");
			bufferedReader.close();
			mongoClient.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}