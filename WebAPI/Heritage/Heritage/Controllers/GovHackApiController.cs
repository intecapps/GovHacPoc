using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Heritage.Helper;
using Heritage.Models;

namespace Heritage.Controllers
{
    public class GovHackApiController : ApiController
    {
        // GET api/govhackapi
        [HttpGet]
        [ActionName("GetPlaceMarkers")]
        public IEnumerable<PlaceMarker> GetPlacemarker(string lat, string lon)
        {
            return MockData.GetPlacemarker(lat, lon);
        }

        [HttpGet]
        [ActionName("GetImages")]
        public IEnumerable<Image> GetImages(string lat, string lon, int placeMarkerId) 
        {
            return MockData.GetImages(lat, lon, placeMarkerId);
        }
    }
}
