using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using GovHacDal;
using GovHacDal.Repository;
using Heritage.Models;
using Heritage.Utilities;
using Newtonsoft.Json;
using Quartz;
using RestSharp;
using RestSharp.Deserializers;

namespace Heritage.Schedule
{
    [DisallowConcurrentExecution]
    public class LastKnownLocationJob : IJob
    {
        private const string PARAMETER = "?title={0}&text={1}&latitude={2}&longitude={3}";

        public void Execute(IJobExecutionContext context)
        {
            ProcessLastKnownLocations();
        }


        private void ProcessLastKnownLocations()
        {
            string lastKnownUrl = ConfigUtil.GetLastKnowLocationUrl();
            var client = new RestClient();
            var request = new RestRequest(lastKnownUrl, Method.GET);

            request.AddHeader("Content-Type", "application/json; charset=utf-8");
            //client.Execute<Person>(request)
            IRestResponse response = client.Execute(request);
            var content = response.Content;

            var jsonObj = Newtonsoft.Json.Linq.JObject.Parse(response.Content);
            var jsonItems = jsonObj["items"];
            var lat = jsonItems.First()["latitude"].ToString();
            var lon = jsonItems.First()["longitude"].ToString();

            // get the place markers
            IList<spGetPlaceMarker_Result> markers = PlacemarkerRepository.GetPlaceMarker(lat, lon);
            
            StringBuilder sb = new StringBuilder();
            sb.Append("<h2>Places near you</h2>");
            sb.Append("<ul>");

            foreach (spGetPlaceMarker_Result res in markers)
            {
                sb.Append(string.Format("<li>{0}</li>", res.Name));
            }
            sb.Append("</ul>");

            string postUrl = ConfigUtil.GetPostToGlassUrl() + string.Format(PARAMETER, "Notification", sb.ToString(), lat, lon);
            request = new RestRequest(postUrl, Method.GET);
            // call the post to glass url
            response = client.Execute(request);
            
            var str = "";
        }
    }
}