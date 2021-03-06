// Copyright 2017 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package adwords.axis.v201708.advancedoperations;

import com.beust.jcommander.Parameter;
import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201708.cm.AdGroupAd;
import com.google.api.ads.adwords.axis.v201708.cm.AdGroupAdOperation;
import com.google.api.ads.adwords.axis.v201708.cm.AdGroupAdReturnValue;
import com.google.api.ads.adwords.axis.v201708.cm.AdGroupAdServiceInterface;
import com.google.api.ads.adwords.axis.v201708.cm.AdGroupAdStatus;
import com.google.api.ads.adwords.axis.v201708.cm.Image;
import com.google.api.ads.adwords.axis.v201708.cm.Operator;
import com.google.api.ads.adwords.axis.v201708.cm.TemplateAd;
import com.google.api.ads.adwords.axis.v201708.cm.TemplateElement;
import com.google.api.ads.adwords.axis.v201708.cm.TemplateElementField;
import com.google.api.ads.adwords.axis.v201708.cm.TemplateElementFieldType;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.factory.AdWordsServicesInterface;
import com.google.api.ads.adwords.lib.utils.examples.ArgumentNames;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.ads.common.lib.utils.examples.CodeSampleParams;
import com.google.api.client.auth.oauth2.Credential;

/**
 * This example adds a click-to-download templateAd to given ad group. To get
 * adGroupId, run AddAdGroup.java.
 *
 * <p>Credentials and properties in {@code fromFile()} are pulled from the
 * "ads.properties" file. See README for more info.
 */
public class AddClickToDownloadAd {

  private static class AddClickToDownloadAdParams extends CodeSampleParams {
    @Parameter(names = ArgumentNames.AD_GROUP_ID, required = true)
    private Long adGroupId;
  }

  public static void main(String[] args) throws Exception {
    // Generate a refreshable OAuth2 credential.
    Credential oAuth2Credential = new OfflineCredentials.Builder()
        .forApi(Api.ADWORDS)
        .fromFile()
        .build()
        .generateCredential();

    // Construct an AdWordsSession.
    AdWordsSession session = new AdWordsSession.Builder()
        .fromFile()
        .withOAuth2Credential(oAuth2Credential)
        .build();

    AdWordsServicesInterface adWordsServices = AdWordsServices.getInstance();

    AddClickToDownloadAdParams params = new AddClickToDownloadAdParams();
    if (!params.parseArguments(args)) {
      // Either pass the required parameters for this example on the command line, or insert them
      // into the code here. See the parameter class definition above for descriptions.
      params.adGroupId = Long.parseLong("INSERT_AD_GROUP_ID_HERE");
    }

    runExample(adWordsServices, session, params.adGroupId);
  }

  public static void runExample(
      AdWordsServicesInterface adWordsServices, AdWordsSession session, long adGroupId)
      throws Exception {
    // Get the AdGroupAdService.
    AdGroupAdServiceInterface adGroupAdService =
        adWordsServices.get(session, AdGroupAdServiceInterface.class);

    // Create the template ad.
    TemplateAd clickToDownloadAppAd = new TemplateAd();
    clickToDownloadAppAd.setName("Ad for jupiter adventure game");
    clickToDownloadAppAd.setTemplateId(353L);

    clickToDownloadAppAd.setFinalUrls(
        new String[] {"http://play.google.com/store/apps/details?id=com.example.jupiteradventure"});
    clickToDownloadAppAd.setDisplayUrl("play.google.com");

    // Create the template elements for the ad. You can refer to
    // https://developers.google.com/adwords/api/docs/appendix/templateads
    // for the list of available template fields.
    TemplateElementField headline = new TemplateElementField();
    headline.setName("headline");
    headline.setFieldText("Enjoy a Jupiter Adventure");
    headline.setType(TemplateElementFieldType.TEXT);

    TemplateElementField description1 = new TemplateElementField();
    description1.setName("description1");
    description1.setFieldText("Realistic physics simulation");
    description1.setType(TemplateElementFieldType.TEXT);

    TemplateElementField description2 = new TemplateElementField();
    description2.setName("description2");
    description2.setFieldText("Race against players online");
    description2.setType(TemplateElementFieldType.TEXT);

    TemplateElementField appId = new TemplateElementField();
    appId.setName("appId");
    appId.setFieldText("com.example.jupiteradventure");
    appId.setType(TemplateElementFieldType.TEXT);

    TemplateElementField appStore = new TemplateElementField();
    appStore.setName("appStore");
    appStore.setFieldText("2");
    appStore.setType(TemplateElementFieldType.ENUM);

    // Optionally specify a landscape image. The image needs to be in a BASE64
    // encoded form. Here we download a demo image and encode it for this ad.
    byte[] imageData =
        com.google.api.ads.common.lib.utils.Media.getMediaDataFromUrl("https://goo.gl/9JmyKk");
    Image image = new Image();
    image.setData(imageData);
    TemplateElementField landscapeImage = new TemplateElementField();
    landscapeImage.setName("landscapeImage");
    landscapeImage.setFieldMedia(image);
    landscapeImage.setType(TemplateElementFieldType.IMAGE);

    TemplateElement adData = new TemplateElement();
    adData.setUniqueName("adData");
    adData.setFields(
        new TemplateElementField[] {
          headline, description1, description2, appId, appStore, landscapeImage
        });

    clickToDownloadAppAd.setTemplateElements(new TemplateElement[] {adData});

    // Create the AdGroupAd.
    AdGroupAd clickToDownloadAppAdGroupAd = new AdGroupAd();
    clickToDownloadAppAdGroupAd.setAdGroupId(adGroupId);
    clickToDownloadAppAdGroupAd.setAd(clickToDownloadAppAd);

    // Optional: Set the status.
    clickToDownloadAppAdGroupAd.setStatus(AdGroupAdStatus.PAUSED);

    // Create the operation.
    AdGroupAdOperation operation = new AdGroupAdOperation();
    operation.setOperator(Operator.ADD);
    operation.setOperand(clickToDownloadAppAdGroupAd);

    // Create the ads.
    AdGroupAdReturnValue result = adGroupAdService.mutate(new AdGroupAdOperation[] {operation});

    for (AdGroupAd adGroupAd : result.getValue()) {
      System.out.printf("New click-to-download ad with ID %d and url %s was created.%n",
          adGroupAd.getAd().getId(), adGroupAd.getAd().getFinalUrls(0));
    }
  }
}
