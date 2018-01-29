from models import Location
from rest_framework import serializers


class LocationSerializer(serializers.ModelSerializer):

    class Meta:
        model = Location
        fields = ('latitude', 'longitude', 'device_identification', 'send_date')


