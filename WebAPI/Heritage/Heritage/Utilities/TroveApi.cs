using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Xml;
using System.Xml.XPath;
using Heritage.Models;
using Newtonsoft.Json;
using RestSharp;

namespace Heritage.Utilities
{
    public static class TroveApi
    {
        private const string QUERY = "?key=qv74ljll8kbpcr4f&q={0}&zone=picture&encoding=json";

        private const string SLSA_URL = "http://images.slsa.sa.gov.au/mpcimgt";

        private const string SLSA_ACTUAL_IMAGE_URL = "http://images.slsa.sa.gov.au/mpcimg";
        
        private const string THUMBNAIL = "thumbnail";

        private const string NAA_FORMAT_URL = "http://recordsearch.naa.gov.au/NAAMedia/ShowImage.asp?B={0}&T=P&S=1";

        private const string NAA_URL = "http://recordsearch.naa.gov.au";

        private const string ART_GALLERY_URL = "http://artsearch.nga.gov.au/Images/SML/";

        private const string ART_GALLERY_LARGE_IMAGE_URL = "http://artsearch.nga.gov.au/Images/LRG/";

        public static List<Image> GetImages(string query)
        {
            List<Image> images = new List<Image>();

            string url = ConfigUtil.GetTroveApiUrl() + string.Format(QUERY, query);

            var client = new RestClient();
            var request = new RestRequest(url, Method.GET);

            request.AddHeader("Content-Type", "application/json; charset=utf-8");
            IRestResponse response = client.Execute(request);
            var content = response.Content;

            var jsonObj = Newtonsoft.Json.Linq.JObject.Parse(content);

            XmlDocument doc = JsonConvert.DeserializeXmlNode(content);

            var imageResults = doc.SelectNodes("response/zone/records/work/identifier");

            for (int i = 0, size = imageResults.Count; i < size; i++)
            {
                XmlNode node = imageResults[i];

                if (!THUMBNAIL.Equals(node.SelectSingleNode("linktype").InnerText))
                {
                    continue;
                }

                string imageUrl = node.SelectSingleNode("value").InnerText;

                Image img = null;
                if (imageUrl.StartsWith(SLSA_URL))
                {
                    img = GetSlsaImage(node, imageUrl);
                    //imageUrl = imageUrl.Replace(SLSA_URL, SLSA_ACTUAL_IMAGE_URL);
                }
                else if (imageUrl.StartsWith(NAA_URL))
                {
                    img = GetNaaImage(node, imageUrl);
                }
                else if (imageUrl.StartsWith(ART_GALLERY_URL))
                {
                    img = GetArtGalleryImage(node, imageUrl);
                }
                

                /*
                XmlNode parentNode = node.ParentNode;
                XmlNode snippet = parentNode.SelectSingleNode("snippet");

                XmlNode issuedNode = parentNode.SelectSingleNode("issued");
                XmlNode titleNode = parentNode.SelectSingleNode("title");

                string desc = (parentNode == null || snippet == null) ? "" : snippet.InnerText;
                string issued = (issuedNode == null) ? "" : issuedNode.InnerText;
                string title = (titleNode == null) ? "" : titleNode.InnerText;

                images.Add(new Image { ImageUrl = imageUrl, Name = desc, Description = desc, PublishDate=issued, Title=title });
                 */
                if (img != null)
                {
                    images.Add(img);
                }

            }
            return images;
        }

        private static Image GetArtGalleryImage(XmlNode node, string imageUrl)
        {
            imageUrl = imageUrl.Replace(ART_GALLERY_URL, ART_GALLERY_LARGE_IMAGE_URL);

            XmlNode parentNode = node.ParentNode;
            XmlNode snippet = parentNode.SelectSingleNode("snippet");

            XmlNode issuedNode = parentNode.SelectSingleNode("issued");
            XmlNode titleNode = parentNode.SelectSingleNode("title");

            string desc = (parentNode == null || snippet == null) ? "" : snippet.InnerText;
            string issued = (issuedNode == null) ? "" : issuedNode.InnerText;
            string title = (titleNode == null) ? "" : titleNode.InnerText;

            return new Image { ImageUrl = imageUrl, Name = desc, Description = desc, PublishDate = issued, Title = title };
        }

        private static Image GetNaaImage(XmlNode node, string imageUrl)
        {
            Image image = null;
            string numStr = "Number=";
            int indexOfImageNumber = imageUrl.IndexOf(numStr);

            if (indexOfImageNumber >= 0)
            {
                string imageNumber = imageUrl.Substring(indexOfImageNumber + numStr.Length);
                imageUrl = string.Format(NAA_FORMAT_URL, imageNumber);

                XmlNode parentNode = node.ParentNode;
                
                XmlNode issuedNode = parentNode.SelectSingleNode("issued");
                XmlNode titleNode = parentNode.SelectSingleNode("title");

                string issued = (issuedNode == null) ? "" : issuedNode.InnerText;
                string title = (titleNode == null) ? "" : titleNode.InnerText;

                return new Image { ImageUrl = imageUrl, Name = title, Description = title, PublishDate = issued, Title = title };
            }

            return image;
        }

        private static Image GetSlsaImage(XmlNode node, string imageUrl)
        {
            imageUrl = imageUrl.Replace(SLSA_URL, SLSA_ACTUAL_IMAGE_URL);

            XmlNode parentNode = node.ParentNode;
            XmlNode snippet = parentNode.SelectSingleNode("snippet");

            XmlNode issuedNode = parentNode.SelectSingleNode("issued");
            XmlNode titleNode = parentNode.SelectSingleNode("title");

            string desc = (parentNode == null || snippet == null) ? "" : snippet.InnerText;
            string issued = (issuedNode == null) ? "" : issuedNode.InnerText;
            string title = (titleNode == null) ? "" : titleNode.InnerText;

            return new Image { ImageUrl = imageUrl, Name = desc, Description = desc, PublishDate = issued, Title = title };

        }
    }
}