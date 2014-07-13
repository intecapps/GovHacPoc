using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;

namespace Heritage.Utilities
{
    public static class ConfigUtil
    {
        public static string GetLastKnowLocationUrl() {
            return GetProperty("LastKnownLocationUrl");
        }

        public static string GetPostToGlassUrl()
        {
            return GetProperty("PostToGlassUrl");
        }

        public static string GetTroveApiUrl()
        {
            return GetProperty("TroveApiUrl");
        }

        /// <summary>
        /// This method returns the passed in property name, if the property name is
        /// null or if the property does not exist then an empty string will be returned.
        /// </summary>
        /// <param name="propertyName">The name of the property to return.</param>
        /// <returns>The value of the property or an empty string if it does not exist.</returns>
        private static string GetProperty(string propertyName)
        {
            string property;

            if (string.IsNullOrWhiteSpace(propertyName))
            {
                property = string.Empty;
            }
            else
            {
                property = ConfigurationManager.AppSettings[propertyName];

                if (string.IsNullOrWhiteSpace(property))
                {
                    property = string.Empty;
                }
            }

            return property;
        }
    
    }
}