using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using GovHacDal.Repository;
using Heritage.Helper;
using Heritage.Models;

namespace Heritage.Controllers
{
    public class JsonResponse<T>
    {
        public T ResponseObject { get; set; }
    }

    public class GovHackApiController : ApiController
    {
        // GET api/govhackapi
        [HttpGet]
        [ActionName("GetPlaceMarkers")]
        public JsonResponse<IEnumerable<PlaceMarker>> GetPlacemarker(string lat, string lon)
        {
            IList<GovHacDal.spGetPlaceMarker_Result> places = PlacemarkerRepository.GetPlaceMarker(lat, lon);
            List<PlaceMarker> convertedPlaces = new List<PlaceMarker>(places.Count());

            convertedPlaces.AddRange(places.Select(p => new PlaceMarker {
                Description = p.Description,
                Name = p.Name,
                PlaceMarkerId = p.ID,
                PlaceMarkerTypeID = p.PlacemarkerTypeID,
                Longitude = p.Long,
                Latitude = p.Lat
            }));

            JsonResponse<IEnumerable<PlaceMarker>> res = new JsonResponse<IEnumerable<PlaceMarker>>();
            res.ResponseObject = convertedPlaces;

            return res;
        }

        [HttpGet]
        [ActionName("GetImages")]
        public JsonResponse<IEnumerable<Image>> GetImages(string lat, string lon, int placeMarkerId) 
        {
            IList<GovHacDal.spGetImages_Result> images = PlacemarkerRepository.GetImages(lat, lon,placeMarkerId);
            List<Image> convertedImages = new List<Image>(images.Count());

            convertedImages.AddRange(images.Select(im => new Image
            {
                Description = im.Description,
                Name = im.Name,
                ImageId = im.ID,
                ImageUrl = im.ImageUrl,
                Latitude = im.Latitude,
                Longitude=im.Longitude,
                ArceNumber = im.AcreNumber
            }));

            JsonResponse<IEnumerable<Image>> res = new JsonResponse<IEnumerable<Image>>();
            res.ResponseObject = convertedImages;

            return res;
        }
    }
}
