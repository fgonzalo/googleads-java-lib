// Copyright 2017 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.ads.adwords.axis.utils.v201705.batchjob;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link MutateResult}.
 */
@RunWith(JUnit4.class)
public class MutateResultTest
    extends com.google.api.ads.adwords.lib.utils.testing.MutateResultTest<
        com.google.api.ads.adwords.axis.v201705.cm.MutateResult, MutateResult,
        com.google.api.ads.adwords.axis.v201705.cm.ErrorList, ErrorList> {

  public MutateResultTest() {
    super(
        com.google.api.ads.adwords.axis.v201705.cm.MutateResult.class,
        MutateResult.class,
        com.google.api.ads.adwords.axis.v201705.cm.ErrorList.class,
        ErrorList.class);
  }
}