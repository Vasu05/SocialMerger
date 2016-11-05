

package com.example.vasuchand.feedgen.youtubefetch;

import com.example.vasuchand.feedgen.youtube;
import com.google.api.services.youtube.model.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsyncLoadTasks extends CommonAsyncTask {


  AsyncLoadTasks(youtube activity) {
    super(activity);
  }

  @Override
  protected void doInBackground() throws IOException {
    List<String> result = new ArrayList<String>();
  //  List<Activity> activities =
       // client.activities().list("snippet").setMine(Boolean.TRUE).execute().getItems();
//    if (activities != null) {
//      for (Activity activity : activities) {
//        String title = "";
//        if (activity.getSnippet().getTitle()!=null){
//          title = activity.getSnippet().getTitle();
//        }
//        result.add(title);
//      }
//    } else {
//      result.add("No activities.");
//    }
  //  activity.taskList = result;
  }

  public static void run(youtube tasksSample) {
    new AsyncLoadTasks(tasksSample).execute();
  }
}
