using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using GovHacDal.Repository;
using Heritage.Helper;
using Heritage.Models;
using Heritage.Utilities;

namespace Heritage.Controllers
{
    public class JsonResponse<T>
    {

        public T ResponseObject { get; set; }
        // 0 = ok, 1= Error
        public int Status { get; set; }

        public string Message { get; set; }
    }

    public class GovHackApiController : ApiController
    {
        public const int ERROR_STATUS = 1;
        
        // GET api/govhackapi
        [HttpGet]
        [ActionName("GetPlaceMarkers")]
        public JsonResponse<IEnumerable<PlaceMarker>> GetPlacemarker(string lat, string lon)
        {
            JsonResponse<IEnumerable<PlaceMarker>> res = new JsonResponse<IEnumerable<PlaceMarker>>();

            try
            {
                IList<GovHacDal.spGetPlaceMarker_Result> places = PlacemarkerRepository.GetPlaceMarker(lat, lon);
                List<PlaceMarker> convertedPlaces = new List<PlaceMarker>(places.Count());

                convertedPlaces.AddRange(places.Select(p => new PlaceMarker
                {
                    Description = p.Description,
                    Name = p.Name,
                    PlaceMarkerId = p.ID,
                    PlaceMarkerTypeID = (p.PlacemarkerTypeID.HasValue) ? p.PlacemarkerTypeID.Value : 0,
                    Longitude = p.Long,
                    Latitude = p.Lat
                }));

                res.ResponseObject = convertedPlaces;
            }
            catch (Exception e)
            {
                res.Status = ERROR_STATUS;
                res.Message = e.Message;
            }
            return res;
        }

        [HttpGet]
        [ActionName("GetImages")]
        public JsonResponse<IEnumerable<Image>> GetImages(string lat, string lon, int placeMarkerId) 
        {
            JsonResponse<IEnumerable<Image>> res = new JsonResponse<IEnumerable<Image>>();
            try
            {
                IList<GovHacDal.spGetImages_Result> images = PlacemarkerRepository.GetImages(lat, lon, placeMarkerId);
                List<Image> convertedImages = new List<Image>(images.Count());

                
                convertedImages.AddRange(images.Select(im => new Image
                {
                    Description = im.Description,
                    Name = im.Name,
                    ImageId = im.ID,
                    ImageUrl = im.ImageUrl,
                    Latitude = im.Latitude,
                    Longitude = im.Longitude,
                    ArceNumber = im.AcreNumber
                }));

                if (placeMarkerId > 0)
                {
                    string name = PlacemarkerRepository.GetPlaceMarkerName(placeMarkerId, true);

                    name = "adelaide GPO sandbagging";
                    List<Image> troveImages = TroveApi.GetImages(name);

                    List<String> convertedImageUrls = convertedImages.Select(ci => ci.ImageUrl).ToList();
                    convertedImages.AddRange(troveImages.Where(im => convertedImageUrls.Contains(im.ImageUrl) == false));
                    
                }
                res.ResponseObject = convertedImages;
            }
            catch (Exception e)
            {
                res.Status = ERROR_STATUS;
                res.Message = e.Message;
            }
            return res;
        }

        [HttpGet]
        [ActionName("Checkin")]
        public JsonResponse<bool> Checkin(int accountId, int placeMarkerId, string comment)
        {
            JsonResponse<bool> res = new JsonResponse<bool>();

            try
            {
                res.ResponseObject = AccountRepository.Checkin(accountId, placeMarkerId, comment);
            }
            catch (Exception e)
            {
                res.Status = ERROR_STATUS;
                res.Message = e.Message;
            }
            return res;
        }

        [HttpGet]
        [ActionName("ImageView")]
        public JsonResponse<bool> ImageView(int accountId, int imageId)
        {
            JsonResponse<bool> res = new JsonResponse<bool>();

            try
            {
                res.ResponseObject = AccountRepository.ImageView(accountId, imageId);
            }
            catch (Exception e)
            {
                res.Status = ERROR_STATUS;
                res.Message = e.Message;
            }
            return res;
        }

        [HttpGet]
        [ActionName("AccountLevels")]
        public JsonResponse<List<AccountLevel>> AccountLevels(int accountId, int imageId)
        {
            JsonResponse<List<AccountLevel>> res = new JsonResponse<List<AccountLevel>>();

            try
            {
                List<AccountLevel> levels = new List<AccountLevel>();

                var accountLevels = AccountRepository.GetAccountLevels(accountId);

                levels.AddRange(accountLevels.Select(al => new AccountLevel
                {
                    AccountId = al.accountId,
                    Description = al.Description,
                    Image = al.imageUrl,
                    Level = al.Name
                }));
                res.ResponseObject = levels;
            }
            catch (Exception e)
            {
                res.Status = ERROR_STATUS;
                res.Message = e.Message;
            }
            return res;
        }
        
    }
}
