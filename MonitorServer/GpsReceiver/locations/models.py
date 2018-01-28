from django.db import models


class Location(models.Model):
    latitude = models.FloatField(null=False,)
    longitude = models.FloatField(null=False)
    device_identification = models.IntegerField(null=False)
    date_time = models.DateTimeField(null=False)

