## Asset Statuses

Considering IoT most of the modern plants or any production facilities these days are equipped with a large number of sensors that send various data about certain assets or processes of the plant. In our fictional plant, we have thousands of sensors that dispatch statuses of assets via messages to some _"real-time queue"_. This fictional queue emits millions of asset status messages per second and on each message, it calls the `handle(Message message)` method of a message processor(s) attached to it.

**NOTE:** you are not expected to implement the above-mentioned _"real-time queue"_.

An asset status can be of a `NORMAL`, `WARNING` or `ERROR` types. A typical asset status message has the following payload:
``` json
{
  "id": "4fda181e-c9c7-4d68-8e31-f9a42dd33aa8",
  "assetId": "8ed2b7ac-fbc5-4afe-ba77-4105459fdd2d",
  "statusType": "NORMAL",
  "createdAt": "2018-10-22T09:43:00.862Z"
}
```

You need to implement a system that should provide a simple API to do the following:
- Allow its consumers to subscribe to certain asset status events and notify them:
  - `AssetCouldFail` event should be emitted when asset status changed to WARNING
  - `AssetFailed` event should be emitted when asset status changed to ERROR
  - `AssetRecovered` event should be emitted when asset status is changed back to NORMAL
- Store asset statuses in such a way to be able to do the following queries without incurring performance penalty (i.e. due to a large number of assets or statuses):
  - Return the current (i.e. latest) asset status by a given asset id
  - Return asset ids for a given status type. For example: show me all assets that are currently failed (i.e. have ERROR status).

**Input:**

Use the provided `asset_statuses.json` file containing 1000 asset status messages in JSON format as an input. Your solution will be tested by executing from shell and passing the json file through an argument. For example:

``` shell
 $ > java YourSolution asset_statuses.json
 // or
 $ > node YourSolution.js asset_statuses.json
```
When executed the bootstrapping function should mock a real-time queue by calling for each message the `handle(Message message)` function on the system you are going to implement.

**Output:**

Upon execution your bootstrapping function should process the JSON input file and report the following:
- Any status changes for assets for example:
  ```
  ...
  Asset 67247f96-1b1a-48e5-82b4-5277d4438b3c is about to fail with status WARNING at 2011-01-03T01:41:48Z
  // here the asset status changed to ERROR from the WARNING status
  Asset 67247f96-1b1a-48e5-82b4-5277d4438b3c has eventually failed with status ERROR at 2011-07-14T18:42:46Z
  Asset 67247f96-1b1a-48e5-82b4-5277d4438b3c has recovered with status NORMAL at 2012-02-16T11:27:28Z
  // here the asset status changed to ERROR from the NORMAL status without a WARNING
  Asset 8786cc43-230d-4ac9-8827-5287f4bfa7d8 has abruptly failed with status ERROR at 2011-07-14T18:42:46Z
  ...
  ```
  *the above `// comments` are for explanation and are not expected in the output

After all the messages have been processed the bootstrap function should also:
- Get current asset statuses by asset ids for the following assets:
  ```
  f1aa3b54-6238-4e67-b3ea-a5bc13b77dd0,
  fbbcaf29-a72f-48f7-8f2b-d5abf8bc24bf,
  4f436a0d-903b-46bd-8e45-46c1dd3dc631
  ```
  which should print the following in stdout:
  ```
  Current status for asset 'f1aa3b54-6238-4e67-b3ea-a5bc13b77dd0' is 'ERROR'
  Current status for asset 'fbbcaf29-a72f-48f7-8f2b-d5abf8bc24bf' is 'NORMAL
  Current status for asset '62d98170-ff7e-4ee1-8f1c-b54f8aed351c' is 'WARNING'
  ```
- Get asset ids by status type `ERROR` which should print the following in stdout:
  ```
  There are 2 assets which are currently in ERROR:
  'f1aa3b54-6238-4e67-b3ea-a5bc13b77dd0',
  '18a5ba9a-7a0f-4c09-a836-8e2ef2da79ca'
  ```
- Get asset ids by status type `WARNING` which should print the following in stdout:
  ```
  There are 10 assets which are currently in ERROR:
  '67247f96-1b1a-48e5-82b4-5277d4438b3c',
  '62d98170-ff7e-4ee1-8f1c-b54f8aed351c',
  '12a594e4-f51f-407e-9d01-679a8323a3c0',
  '8fded256-979f-402c-aacc-8ab6b98787d1',
  '8786cc43-230d-4ac9-8827-5287f4bfa7d8',
  '4f436a0d-903b-46bd-8e45-46c1dd3dc631',
  '5cd0c05f-2a39-4ea5-b0ea-0dedb3e5b968',
  '91acbf58-e7ea-4790-b58a-7442d2a32109',
  'e4c52414-9776-451d-ba80-f3f46b77686b',
  'd4250e33-f81f-408f-855a-36414fe4cee5'
  ```

**Assumptions/Expectations:**
- For brevity assume that storing of asset statuses using in-memory data structures should be sufficient. (i.e. no need for DB)
- You can use the programming language of your choice.
- You should not need to use any framework for this exercise.
- The provided solution should be executable when you feed it the mock data file `asset_statuses.json`
- **Bonus points** if you provide unit tests along with your implementation.

Submit your solution by forking this repository and creating a Pull Request on GitHub. This will allow our developers to provide comments to your proposed solution.
