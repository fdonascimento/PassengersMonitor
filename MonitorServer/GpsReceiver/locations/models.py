from django.db import models


class Location(models.Model):
    latitude = models.FloatField(null=False)
    longitude = models.FloatField(null=False)
    device_identification = models.CharField(null=False, blank=False, max_length=200)
    send_date = models.DateTimeField(null=False)

